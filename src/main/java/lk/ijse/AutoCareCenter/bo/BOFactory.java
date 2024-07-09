package lk.ijse.AutoCareCenter.bo;

import lk.ijse.AutoCareCenter.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,VEHICLE,BOOKING,EMPLOYEE,SUPPLIER,MATERIAL,MATERIALDETAILS,PO,SALARY,REPAIR
    }
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case BOOKING:
                return new BookingBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case MATERIAL:
                return new MaterialBOImpl();
            case PO:
                return new PurchaseOrderBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case REPAIR:
                return new RepairBOImpl();
                case MATERIALDETAILS:
                    return new MaterialDetailBOImpl();
            default:
                return null;
        }
    }

}

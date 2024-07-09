package lk.ijse.AutoCareCenter.bo.custom.impl;

import lk.ijse.AutoCareCenter.bo.custom.PurchaseOrderBO;
import lk.ijse.AutoCareCenter.dao.DAOFactory;
import lk.ijse.AutoCareCenter.dao.custom.*;
import lk.ijse.AutoCareCenter.db.DbConnection;
import lk.ijse.AutoCareCenter.entity.MaterialDetails;
import lk.ijse.AutoCareCenter.entity.OrderDetails;
import lk.ijse.AutoCareCenter.entity.Orders;
import lk.ijse.AutoCareCenter.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    MaterialDAO materialDAO = (MaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MATERIAL);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING);
    MaterialDetailDAO materialDetailDAO = (MaterialDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MATERIALDETAIL);

    @Override
    public boolean exist(String orderId) throws SQLException, ClassNotFoundException {

        return orderDAO.exist(orderId);
    }

    @Override
    public String currentId() throws SQLException, ClassNotFoundException {
        return orderDAO.currentId();
    }

    @Override
    public boolean saveOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            boolean b1 = orderDAO.exist(dto.getOrderId());
            System.out.println("awa1");

            /*if order id already exist*/
            if (b1) {
                return false;

            }

            connection.setAutoCommit(false);
            //Save the Order to the order table
            boolean b2 = orderDAO.save(new Orders(dto.getOrderId(), dto.getCusId(), dto.getDate(), dto.getBId()));
            System.out.println("save");

            if (!b2) {
                System.out.println("awa2");
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailsDTO d : dto.getOrderDetails()) {
                System.out.println("awa");
                OrderDetails orderDetails = new OrderDetails(d.getOrderId(), d.getCode(), d.getQty(), d.getUnitPrice(), d.getService_charge(), d.getTotal());
                boolean b3 = orderDetailDAO.save(orderDetails);
                System.out.println("save");
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                //Search & Update Item
                MaterialDetailsDTO materialDetails = findItem(d.getCode());
                materialDetails.setQtyOnHand(materialDetails.getQtyOnHand() - d.getQty());

                //update item
                boolean b = materialDetailDAO.update(new MaterialDetails(materialDetails.getCode(), materialDetails.getSupId(), materialDetails.getDescription(), materialDetails.getUnitPrice(), materialDetails.getQtyOnHand()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public MaterialDetailsDTO findItem(String code) throws SQLException, ClassNotFoundException {
        try {
            MaterialDetails i = materialDetailDAO.searchById(code);
            return new MaterialDetailsDTO(i.getCode(), i.getSupId(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Material " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getMaterialsIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getBooking() throws SQLException, ClassNotFoundException {
        return null;
    }

}

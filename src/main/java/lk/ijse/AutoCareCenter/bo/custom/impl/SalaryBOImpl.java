package lk.ijse.AutoCareCenter.bo.custom.impl;

import lk.ijse.AutoCareCenter.bo.custom.SalaryBO;
import lk.ijse.AutoCareCenter.dao.DAOFactory;
import lk.ijse.AutoCareCenter.dao.custom.SalaryDAO;
import lk.ijse.AutoCareCenter.entity.Booking;
import lk.ijse.AutoCareCenter.entity.Salary;
import lk.ijse.AutoCareCenter.model.BookingDTO;
import lk.ijse.AutoCareCenter.model.SalaryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryBOImpl implements SalaryBO {
    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);
    @Override
    public ArrayList<SalaryDTO> loadAll() throws SQLException, ClassNotFoundException {
        ArrayList<SalaryDTO> allSalary = new ArrayList<>();
        ArrayList<Salary> all = salaryDAO.loadAll();

        for (Salary s : all) {
            allSalary.add(new SalaryDTO(s.getId(), s.getAmount(), s.getMonth(), s.getEmpId()));
        }
        return allSalary;
    }

    @Override
    public boolean save(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        return salaryDAO.save(new Salary(salaryDTO.getId(), salaryDTO.getAmount(), salaryDTO.getMonth(), salaryDTO.getEmpId()));
    }

    @Override
    public boolean update(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        return salaryDAO.update(new Salary(salaryDTO.getId(), salaryDTO.getAmount(), salaryDTO.getMonth(), salaryDTO.getEmpId()));
    }

    @Override
    public Salary searchById(String id) throws SQLException, ClassNotFoundException {
        return salaryDAO.searchById(id);
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return salaryDAO.delete(id);
    }

    @Override
    public String currentId() throws SQLException, ClassNotFoundException {
        return salaryDAO.currentId();
    }
}

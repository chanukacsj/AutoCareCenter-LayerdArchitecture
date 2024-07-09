package lk.ijse.AutoCareCenter.bo.custom.impl;

import lk.ijse.AutoCareCenter.bo.custom.RepairBO;
import lk.ijse.AutoCareCenter.dao.DAOFactory;
import lk.ijse.AutoCareCenter.dao.custom.RepairDAO;
import lk.ijse.AutoCareCenter.entity.Booking;
import lk.ijse.AutoCareCenter.entity.Repair;
import lk.ijse.AutoCareCenter.model.BookingDTO;
import lk.ijse.AutoCareCenter.model.RepairDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairBOImpl implements RepairBO {
    RepairDAO repairDAO = (RepairDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REPAIR);

    @Override
    public ArrayList<RepairDTO> loadAll() throws SQLException, ClassNotFoundException {
        ArrayList<RepairDTO> allRepair = new ArrayList<>();
        ArrayList<Repair> all = repairDAO.loadAll();

        for (Repair r : all) {
            allRepair.add(new RepairDTO(r.getRId(), r.getStartTime(), r.getEndTime(), r.getDescription(), r.getEmpId(), r.getVId()));
        }
        return allRepair;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(RepairDTO repairDTO) throws SQLException, ClassNotFoundException {
        return repairDAO.save(new Repair(repairDTO.getRId(), repairDTO.getStartTime(), repairDTO.getEndTime(), repairDTO.getDescription(), repairDTO.getEmpId(), repairDTO.getVId()));
    }

    @Override
    public boolean update(RepairDTO repairDTO) throws SQLException, ClassNotFoundException {
        return repairDAO.update(new Repair(repairDTO.getRId(), repairDTO.getStartTime(), repairDTO.getEndTime(), repairDTO.getDescription(), repairDTO.getEmpId(), repairDTO.getVId()));
    }

    @Override
    public Repair searchById(String id) throws SQLException, ClassNotFoundException {
        return repairDAO.searchById(id);
    }
}

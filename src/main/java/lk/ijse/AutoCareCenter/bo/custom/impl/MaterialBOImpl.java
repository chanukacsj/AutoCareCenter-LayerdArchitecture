package lk.ijse.AutoCareCenter.bo.custom.impl;

import lk.ijse.AutoCareCenter.bo.custom.MaterialBO;
import lk.ijse.AutoCareCenter.dao.DAOFactory;
import lk.ijse.AutoCareCenter.dao.custom.MaterialDAO;
import lk.ijse.AutoCareCenter.entity.Materials;
import lk.ijse.AutoCareCenter.model.MaterialsDTO;

import java.sql.SQLException;
import java.util.List;

public class MaterialBOImpl implements MaterialBO {
    MaterialDAO materialDAO = (MaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MATERIAL);

    @Override
    public boolean save(MaterialsDTO materialsDTO) throws SQLException, ClassNotFoundException {
        return materialDAO.save(new Materials(materialsDTO.getCode()));
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return materialDAO.delete(code);
    }

    @Override
    public List<String> getCodes() throws SQLException, ClassNotFoundException {
        return materialDAO.getCodes();
    }

    @Override
    public int getMaterialCount() throws SQLException, ClassNotFoundException {
        return materialDAO.getMaterialCount();
    }
}

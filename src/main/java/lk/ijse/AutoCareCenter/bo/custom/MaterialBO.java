package lk.ijse.AutoCareCenter.bo.custom;

import lk.ijse.AutoCareCenter.bo.SuperBO;
import lk.ijse.AutoCareCenter.dao.SqlUtil;
import lk.ijse.AutoCareCenter.entity.Materials;
import lk.ijse.AutoCareCenter.model.MaterialsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MaterialBO extends SuperBO {
    public boolean save(MaterialsDTO materialsDTO) throws SQLException, ClassNotFoundException;

    public boolean delete(String code) throws SQLException, ClassNotFoundException;

    public List<String> getCodes() throws SQLException, ClassNotFoundException;

    public int getMaterialCount() throws SQLException, ClassNotFoundException;
}
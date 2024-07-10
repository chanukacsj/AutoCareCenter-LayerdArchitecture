package lk.ijse.AutoCareCenter.dao.custom.Impl;

import lk.ijse.AutoCareCenter.dao.SqlUtil;
import lk.ijse.AutoCareCenter.dao.custom.MaterialDAO;
import lk.ijse.AutoCareCenter.entity.Materials;
import lk.ijse.AutoCareCenter.model.OrdersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAOImpl implements MaterialDAO {


    public boolean save(Materials entity) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("INSERT INTO materials VALUES(?)", entity.getCode());
    }

    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("DELETE FROM materials WHERE code = ?", code);
    }
    public List<String> getCodes() throws SQLException, ClassNotFoundException {
        List<String> codes = new ArrayList<>();

        ResultSet rst = SqlUtil.execute("SELECT code FROM materials");
        while (rst.next()) {
            codes.add(rst.getString(1));
        }

        return codes;
    }
    public int getMaterialCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT COUNT(*) AS materials_count FROM materials");

        if (rst.next()) {
            return rst.getInt("materials_count");
        }
        return 0;
    }

    @Override
    public String currentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT code FROM materials ORDER BY code desc LIMIT 1");

        if(rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<Materials> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean update(Materials DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Materials searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}

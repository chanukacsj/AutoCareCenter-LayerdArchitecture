package lk.ijse.AutoCareCenter.dao.custom.Impl;

import lk.ijse.AutoCareCenter.dao.SqlUtil;
import lk.ijse.AutoCareCenter.dao.custom.MaterialDetailDAO;
import lk.ijse.AutoCareCenter.entity.MaterialDetails;
import lk.ijse.AutoCareCenter.model.OrdersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDetailsDAOImpl implements MaterialDetailDAO {

    public ArrayList<MaterialDetails> loadAll() throws SQLException, ClassNotFoundException {

        ArrayList<MaterialDetails> allMaterialDetails = new ArrayList<>();
        ResultSet rst = SqlUtil.execute("SELECT * FROM material_details");
        while (rst.next()) {
            allMaterialDetails.add(new MaterialDetails(rst.getString("code"), rst.getString("supId"), rst.getString("description"), rst.getDouble("unit_price"), rst.getInt("qty_on_hand")));
        }
        return allMaterialDetails;
    }

    public boolean save(MaterialDetails entity) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("INSERT INTO material_details VALUES(?,?,?,?,?)", entity.getCode(), entity.getSupId(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand());
    }


    public boolean update(MaterialDetails entity) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("UPDATE material_details SET supId = ?,description = ?,unit_price = ?,qty_on_hand = ? WHERE code = ?", entity.getSupId(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getCode());
    }


    public MaterialDetails searchById(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM material_details WHERE code = ?", code);
        rst.next();
        return new MaterialDetails(code + "", rst.getString("supId"), rst.getString("description"), rst.getDouble("unit_price"), rst.getInt("qty_on_hand"));

    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getMaterialCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public String currentId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }


}

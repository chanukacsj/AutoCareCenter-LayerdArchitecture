package lk.ijse.AutoCareCenter.dao.custom.Impl;

import lk.ijse.AutoCareCenter.dao.SqlUtil;
import lk.ijse.AutoCareCenter.dao.custom.SupplierDAO;
import lk.ijse.AutoCareCenter.entity.Supplier;
import lk.ijse.AutoCareCenter.model.OrdersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    public ArrayList<Supplier> loadAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM suppliers");

        ArrayList<Supplier> allSuppliers = new ArrayList<>();

        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("supId"), rst.getString("supName"), rst.getString("contact"), rst.getString("address"));
            allSuppliers.add(supplier);
        }

        return allSuppliers;
    }

    public boolean save(Supplier entity) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("INSERT INTO suppliers VALUES(?, ?, ?, ?)", entity.getId(), entity.getName(), entity.getContact(), entity.getAddress());
    }

    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("UPDATE suppliers SET supName = ?, contact = ?,address = ? WHERE supId = ?", entity.getName(), entity.getContact(), entity.getAddress(), entity.getId());
    }

    public Supplier searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM suppliers WHERE supId = ?", id);
        rst.next();

        return new Supplier(id + "", rst.getString("supName"), rst.getString("contact"), rst.getString("address"));

    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("DELETE FROM suppliers WHERE supId = ?", id);
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> ids = new ArrayList<>();

        ResultSet rst = SqlUtil.execute("SELECT supId FROM suppliers");
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
            return ids;
        }

    @Override
    public int getMaterialCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}


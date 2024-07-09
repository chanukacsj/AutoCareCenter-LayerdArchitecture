package lk.ijse.AutoCareCenter.dao.custom.Impl;

import lk.ijse.AutoCareCenter.dao.SqlUtil;
import lk.ijse.AutoCareCenter.dao.custom.OrderDAO;
import lk.ijse.AutoCareCenter.entity.Orders;
import lk.ijse.AutoCareCenter.model.OrdersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    public String currentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT orderId FROM orders ORDER BY orderId desc LIMIT 1");

        if(rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public boolean exist(String orderId) throws SQLException, ClassNotFoundException {
       ResultSet rst = SqlUtil.execute("SELECT orderId FROM orders WHERE orderId = ?",orderId);

       return rst.next();
    }



    public int getOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT COUNT(*) AS order_count FROM orders");

        if(rst.next()) {
            return rst.getInt("order_count");
        }
        return 0;
    }

    @Override
    public ArrayList<Orders> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Orders DTO) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("INSERT INTO orders VALUES(?,?,?,?)",DTO.getOrderId(),DTO.getCusId(),DTO.getDate(),DTO.getBId());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getMaterialCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}

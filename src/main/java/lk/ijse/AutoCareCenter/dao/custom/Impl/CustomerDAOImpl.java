package lk.ijse.AutoCareCenter.dao.custom.Impl;

import lk.ijse.AutoCareCenter.dao.SqlUtil;
import lk.ijse.AutoCareCenter.dao.custom.CustomerDAO;
import lk.ijse.AutoCareCenter.entity.Customer;
import lk.ijse.AutoCareCenter.model.OrdersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    public ArrayList<Customer> loadAll() throws SQLException, ClassNotFoundException {

       ArrayList<Customer> allCustomer = new ArrayList<>();
        ResultSet rst =  SqlUtil.execute("SELECT * FROM customers");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("id"), rst.getString("name"), rst.getString("contact"), rst.getString("address"));
            allCustomer.add(customer);
        }
        return allCustomer;
    }

    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {

        return SqlUtil.execute("INSERT INTO customers VALUES(?, ?, ?, ?)", entity.getId(), entity.getName(),  entity.getContact(),entity.getAddress());

    }

    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("UPDATE customers SET name=?, contact=? ,address=? WHERE id=?", entity.getName(),entity.getContact(), entity.getAddress(), entity.getId());
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SqlUtil.execute("DELETE FROM customers WHERE id = ?", id);
    }

    public Customer searchById(String id) throws SQLException, ClassNotFoundException {

        ResultSet rst =  SqlUtil.execute("SELECT * FROM customers WHERE id = ?",id);
        rst.next();
        return new Customer(id + "", rst.getString("name"), rst.getString("address"),rst.getString("contact"));

    }


    public List<String> getIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> ids = new ArrayList<>();

            ResultSet rst = SqlUtil.execute("SELECT id FROM customers");
            while (rst.next()) {
                ids.add(rst.getString(1));
            }

        return ids;
    }

    @Override
    public int getMaterialCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    public int getCustomerCount() throws SQLException, ClassNotFoundException {
      ResultSet rst =  SqlUtil.execute("SELECT COUNT(*) AS customer_count FROM customers");
      if(rst.next()){
          return rst.getInt("customer_count");
      }
      return 0;
    }

}

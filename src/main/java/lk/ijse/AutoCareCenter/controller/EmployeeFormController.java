package lk.ijse.AutoCareCenter.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import lk.ijse.AutoCareCenter.Util.Regex;
import lk.ijse.AutoCareCenter.bo.BOFactory;
import lk.ijse.AutoCareCenter.bo.custom.CustomerBO;
import lk.ijse.AutoCareCenter.bo.custom.EmployeeBO;
import lk.ijse.AutoCareCenter.dao.custom.Impl.EmployeeDAOImpl;
import lk.ijse.AutoCareCenter.entity.Employee;
import lk.ijse.AutoCareCenter.model.EmployeeDTO;


import lk.ijse.AutoCareCenter.model.tm.EmployeeTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    Integer index;

    public void initialize() {
        //this.employeeList = getAllEmployee();
        setCellValueFactory();
        // loadEmployeeTable();
        loadAllEmployee();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }
    @FXML
    void getEmployee(MouseEvent event) {
        index = tblEmployee.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        } else {
            txtId.setText(tblEmployee.getItems().get(index).getId());
            txtName.setText(tblEmployee.getItems().get(index).getName());
            txtAddress.setText(tblEmployee.getItems().get(index).getAddress());
            txtContact.setText(tblEmployee.getItems().get(index).getContact());
        }
    }

    public void loadAllEmployee() {
        tblEmployee.getItems().clear();

        try {

            ArrayList<EmployeeDTO> employeeDTOS = employeeBO.loadAll();

            for (EmployeeDTO employeeDTO : employeeDTOS) {
                tblEmployee.getItems().add(new EmployeeTm(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getAddress(), employeeDTO.getContact()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = employeeBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllEmployee();
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        EmployeeDTO employeeDTO = new EmployeeDTO(id, name, address, contact);
        if (isValid()) {
            try {
                boolean isSaved = employeeBO.save(employeeDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadAllEmployee();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();


        EmployeeDTO employeeDTO = new EmployeeDTO(id, name, address, contact);
        if (isValid()) {
            try {
                boolean isUpdated = employeeBO.update(employeeDTO);
                System.out.println("cscsc");

                if (isUpdated) {
                    System.out.println("dow");
                    new Alert(Alert.AlertType.CONFIRMATION, " employee updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadAllEmployee();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            Employee employeeDTO = employeeBO.searchById(id);

            if (employeeDTO != null) {
                txtId.setText(employeeDTO.getId());
                txtName.setText(employeeDTO.getName());
                txtAddress.setText(employeeDTO.getAddress());
                txtContact.setText(employeeDTO.getContact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValid() {
        if (!Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.ID, (JFXTextField) txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.NAME, (JFXTextField) txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.CONTACT, (JFXTextField) txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.ADDRESS, (JFXTextField) txtAddress)) return false;
        return true;
    }

    public void txtIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.ID, (JFXTextField) txtId);

    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.CONTACT, (JFXTextField) txtContact);

    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.ADDRESS, (JFXTextField) txtAddress);
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.NAME, (JFXTextField) txtName);
    }
}
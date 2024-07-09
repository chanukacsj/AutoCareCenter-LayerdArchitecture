package lk.ijse.AutoCareCenter.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.AutoCareCenter.Util.Regex;
import lk.ijse.AutoCareCenter.bo.BOFactory;
import lk.ijse.AutoCareCenter.bo.custom.CustomerBO;
import lk.ijse.AutoCareCenter.bo.custom.VehicleBO;
import lk.ijse.AutoCareCenter.dao.custom.Impl.CustomerDAOImpl;
import lk.ijse.AutoCareCenter.dao.custom.Impl.VehicleDAOImpl;
import lk.ijse.AutoCareCenter.entity.Customer;
import lk.ijse.AutoCareCenter.entity.Vehicle;
import lk.ijse.AutoCareCenter.model.CustomerDTO;
import lk.ijse.AutoCareCenter.model.VehicleDTO;

import lk.ijse.AutoCareCenter.model.tm.VehicleTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleFormController {
    @FXML
    private JFXComboBox<String> CmbCusId;


    @FXML
    private TableColumn<?, ?> colCusId;


    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblCustomerName;
    @FXML
    private TableView<VehicleTm> tblVehicle;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtType;
    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VEHICLE);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    Integer index;
    public void initialize() {
        setCellValueFactory();
        getCustomerIds();
        loadAllVehicles();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
    }
    public void getVehicle(MouseEvent mouseEvent) {
        index = tblVehicle.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        } else {

            txtId.setText(tblVehicle.getItems().get(index).getId());
            txtType.setText(tblVehicle.getItems().get(index).getType());
            txtNumber.setText(tblVehicle.getItems().get(index).getNumber());
            CmbCusId.setValue(tblVehicle.getItems().get(index).getCusId());
        }
    }

    public void loadAllVehicles() {
        tblVehicle.getItems().clear();
        try {
            ArrayList<VehicleDTO> vehicleDTOS = vehicleBO.loadAll();

            for (VehicleDTO vehicleDTO : vehicleDTOS) {
                tblVehicle.getItems().add(new VehicleTm(vehicleDTO.getId(), vehicleDTO.getType(), vehicleDTO.getNumber(), vehicleDTO.getCusId()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = vehicleBO.delete(id);
            if (isDeleted) {
                System.out.println(" deleted");
                new Alert(Alert.AlertType.CONFIRMATION, "vehicle deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllVehicles();
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String type = txtType.getText();
        String number = txtNumber.getText();
        String cusId = CmbCusId.getValue();

        if (isValid()) {
            VehicleDTO vehicleDTO = new VehicleDTO(id, type, number, cusId);

            try {
                boolean isSaved = vehicleBO.save(vehicleDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "vehicle saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadAllVehicles();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String type = txtType.getText();
        String number = txtNumber.getText();
        String cusId = CmbCusId.getValue();


        VehicleDTO vehicleDTO = new VehicleDTO(id, type, number, cusId);

        if (isValid()) {
            try {
                boolean isUpdated = vehicleBO.update(vehicleDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "vehicle updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loadAllVehicles();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();


        try {
            Vehicle vehicleDTO = vehicleBO.searchById(id);

            if (vehicleDTO != null) {
                txtId.setText(vehicleDTO.getId());
                txtType.setText(vehicleDTO.getType());
                txtNumber.setText(vehicleDTO.getNumber());
                CmbCusId.setValue(vehicleDTO.getCusId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void getCustomerIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {


            List<String> idList = customerBO.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            CmbCusId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cmbCustomerOnAction(ActionEvent actionEvent) {

        String cusId = CmbCusId.getValue();

        try {
            Customer customerDTO = customerBO.searchById(cusId);

            lblCustomerName.setText(customerDTO.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtType.setText("");
        txtNumber.setText("");
    }


    public boolean isValid() {
        if (!Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.ID, (JFXTextField) txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.NAME, (JFXTextField) txtType)) return false;
        return true;
    }

    public void txtVehicleIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.ID, (JFXTextField) txtId);
    }


    public void txtVehicleNumberOnKeyReleased(KeyEvent keyEvent) {
    }

    public void txtVehicleTypeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.AutoCareCenter.Util.TextField.NAME, (JFXTextField) txtType);
    }


}

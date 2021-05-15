package employee;

import control.ControlEmployee;
import control.ControlTurn;
import dominio.Employee;
import dominio.Message;
import dominio.Turn;
import negocio.exceptions.NonexistentEntityException;
import negocio.exceptions.PreexistingEntityException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    private static final ControlEmployee controlEmployee = new ControlEmployee();
    private static final ControlTurn controlTurn = new ControlTurn();

    public Message handleRequest(Message message) {

        System.out.println("EmployeeManager received request of type: " + message.type);

        switch (message.type) {
            case LOGIN:
                return loginEmployee(message);
            case ADD_EMPLOYEE:
                return addEmployee(message);
            case EDIT_EMPLOYEE:
                return editEmployee(message);
            case DELETE_EMPLOYEE:
                return deleteEmployee(message);
            case GET_MANY_EMPLOYEE:
                return getManyEmployee(message);
//            case GET_ONE_EMPLOYEE:
//                return getOneEmployee(message);
//            case GET_EMPLOYEE_BY_PROFILE:
//                return getEmployeeByProfile(message);
//            case GET_EMPLOYEE_BY_ATTENTION_POINT:
//                return getEmployeeByAttentionPoint(message);
//            case GET_EMPLOYEE_BY_BRANCH:
//                return getEmployeeByBranch(message);
            default:
                throw new IllegalStateException("Unexpected value: " + message.type);
        }
    }

    private Message loginEmployee(Message message) {
        String[] userAndPassword = (String[]) message.getObject();

        String user = userAndPassword[0];
        String password = userAndPassword[1];

        List<Employee> employees = new ArrayList<>();

        Employee empleado = null;

        try {
            employees = controlEmployee.findEmployeeByaccountAndPassword(user, password);
            empleado = employees.isEmpty() ? null : employees.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        message.setObject(empleado);

        return message;
    }

    private Message addEmployee(Message message) {
        Employee employee = (Employee) message.getObject();

        List<Employee> employees = new ArrayList<>();

        try {
            controlEmployee.create(employee);
            employees = controlEmployee.findEmployeeEntities();

        } catch (PreexistingEntityException e) {
            System.out.println("El empleado ya existe.");
        }

        message.setObject(employees);

        return message;
    }

    private Message editEmployee(Message message) {
        Employee employee = (Employee) message.getObject();

        List<Turn> turnos = null;

        try {
            turnos = controlTurn.findTurnByIdEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        employee.setTurnList(turnos);
        List<Employee> employees = new ArrayList<>();

        try {
            controlEmployee.edit(employee);
            employees = controlEmployee.findEmployeeEntities();
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (PreexistingEntityException e) {
            System.out.println("El empleado ya existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        message.setObject(employees);

        return message;
    }

    private Message deleteEmployee(Message message) {
        Long idEmployee = (Long) message.getObject();

        try {
            controlEmployee.destroy(idEmployee);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Employee> employees = controlEmployee.findEmployeeEntities();
        message.setObject(employees);

        return message;
    }

    private Message getManyEmployee(Message message) {
        List<Employee> employees = controlEmployee.findEmployeeEntities();
        message.setObject(employees);

        return message;
    }

//    private Message getOneEmployee(Message message) {
//    }
//
//    private Message getEmployeeByProfile(Message message) {
//    }
//
//    private Message getEmployeeByAttentionPoint(Message message) {
//    }
//
//    private Message getEmployeeByBranch(Message message) {
//    }


}

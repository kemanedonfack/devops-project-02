import axios from 'axios';

class EmployeeService {

    getEmployees(){
        return axios.get('/all');
    }

    createEmployee(employee){
        return axios.post('/create', employee);
    }

    getEmployeeById(employeeId){
        return axios.get('/' + employeeId);
    }

    updateEmployee(employee, employeeId){
        return axios.put('/' + employeeId, employee);
    }

    deleteEmployee(employeeId){
        return axios.delete('/' + employeeId);
    }
}

export default new EmployeeService()
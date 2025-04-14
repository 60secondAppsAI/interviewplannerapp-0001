import http from "../http-common"; 

class DepartmentService {
  getAllDepartments(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/department/departments`, searchDTO);
  }

  get(departmentId) {
    return this.getRequest(`/department/${departmentId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/department?field=${matchData}`, null);
  }

  addDepartment(data) {
    return http.post("/department/addDepartment", data);
  }

  update(data) {
  	return http.post("/department/updateDepartment", data);
  }
  
  uploadImage(data,departmentId) {
  	return http.postForm("/department/uploadImage/"+departmentId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new DepartmentService();

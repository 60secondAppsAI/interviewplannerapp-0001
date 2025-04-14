import http from "../http-common"; 

class ApplicationFormService {
  getAllApplicationForms(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/applicationForm/applicationForms`, searchDTO);
  }

  get(applicationFormId) {
    return this.getRequest(`/applicationForm/${applicationFormId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/applicationForm?field=${matchData}`, null);
  }

  addApplicationForm(data) {
    return http.post("/applicationForm/addApplicationForm", data);
  }

  update(data) {
  	return http.post("/applicationForm/updateApplicationForm", data);
  }
  
  uploadImage(data,applicationFormId) {
  	return http.postForm("/applicationForm/uploadImage/"+applicationFormId, data);
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

export default new ApplicationFormService();

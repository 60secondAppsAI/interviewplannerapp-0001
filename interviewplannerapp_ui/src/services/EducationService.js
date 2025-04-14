import http from "../http-common"; 

class EducationService {
  getAllEducations(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/education/educations`, searchDTO);
  }

  get(educationId) {
    return this.getRequest(`/education/${educationId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/education?field=${matchData}`, null);
  }

  addEducation(data) {
    return http.post("/education/addEducation", data);
  }

  update(data) {
  	return http.post("/education/updateEducation", data);
  }
  
  uploadImage(data,educationId) {
  	return http.postForm("/education/uploadImage/"+educationId, data);
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

export default new EducationService();

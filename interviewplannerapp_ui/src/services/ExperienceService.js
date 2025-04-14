import http from "../http-common"; 

class ExperienceService {
  getAllExperiences(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/experience/experiences`, searchDTO);
  }

  get(experienceId) {
    return this.getRequest(`/experience/${experienceId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/experience?field=${matchData}`, null);
  }

  addExperience(data) {
    return http.post("/experience/addExperience", data);
  }

  update(data) {
  	return http.post("/experience/updateExperience", data);
  }
  
  uploadImage(data,experienceId) {
  	return http.postForm("/experience/uploadImage/"+experienceId, data);
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

export default new ExperienceService();

import http from "../http-common"; 

class JobDescriptionService {
  getAllJobDescriptions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/jobDescription/jobDescriptions`, searchDTO);
  }

  get(jobDescriptionId) {
    return this.getRequest(`/jobDescription/${jobDescriptionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/jobDescription?field=${matchData}`, null);
  }

  addJobDescription(data) {
    return http.post("/jobDescription/addJobDescription", data);
  }

  update(data) {
  	return http.post("/jobDescription/updateJobDescription", data);
  }
  
  uploadImage(data,jobDescriptionId) {
  	return http.postForm("/jobDescription/uploadImage/"+jobDescriptionId, data);
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

export default new JobDescriptionService();

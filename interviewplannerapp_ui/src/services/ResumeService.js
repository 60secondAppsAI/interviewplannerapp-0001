import http from "../http-common"; 

class ResumeService {
  getAllResumes(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/resume/resumes`, searchDTO);
  }

  get(resumeId) {
    return this.getRequest(`/resume/${resumeId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/resume?field=${matchData}`, null);
  }

  addResume(data) {
    return http.post("/resume/addResume", data);
  }

  update(data) {
  	return http.post("/resume/updateResume", data);
  }
  
  uploadImage(data,resumeId) {
  	return http.postForm("/resume/uploadImage/"+resumeId, data);
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

export default new ResumeService();

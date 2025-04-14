import http from "../http-common"; 

class InterviewerService {
  getAllInterviewers(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/interviewer/interviewers`, searchDTO);
  }

  get(interviewerId) {
    return this.getRequest(`/interviewer/${interviewerId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/interviewer?field=${matchData}`, null);
  }

  addInterviewer(data) {
    return http.post("/interviewer/addInterviewer", data);
  }

  update(data) {
  	return http.post("/interviewer/updateInterviewer", data);
  }
  
  uploadImage(data,interviewerId) {
  	return http.postForm("/interviewer/uploadImage/"+interviewerId, data);
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

export default new InterviewerService();

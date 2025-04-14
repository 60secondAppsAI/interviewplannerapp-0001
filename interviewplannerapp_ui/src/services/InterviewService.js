import http from "../http-common"; 

class InterviewService {
  getAllInterviews(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/interview/interviews`, searchDTO);
  }

  get(interviewId) {
    return this.getRequest(`/interview/${interviewId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/interview?field=${matchData}`, null);
  }

  addInterview(data) {
    return http.post("/interview/addInterview", data);
  }

  update(data) {
  	return http.post("/interview/updateInterview", data);
  }
  
  uploadImage(data,interviewId) {
  	return http.postForm("/interview/uploadImage/"+interviewId, data);
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

export default new InterviewService();

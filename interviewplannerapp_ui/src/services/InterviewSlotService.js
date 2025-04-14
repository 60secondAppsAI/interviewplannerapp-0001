import http from "../http-common"; 

class InterviewSlotService {
  getAllInterviewSlots(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/interviewSlot/interviewSlots`, searchDTO);
  }

  get(interviewSlotId) {
    return this.getRequest(`/interviewSlot/${interviewSlotId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/interviewSlot?field=${matchData}`, null);
  }

  addInterviewSlot(data) {
    return http.post("/interviewSlot/addInterviewSlot", data);
  }

  update(data) {
  	return http.post("/interviewSlot/updateInterviewSlot", data);
  }
  
  uploadImage(data,interviewSlotId) {
  	return http.postForm("/interviewSlot/uploadImage/"+interviewSlotId, data);
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

export default new InterviewSlotService();

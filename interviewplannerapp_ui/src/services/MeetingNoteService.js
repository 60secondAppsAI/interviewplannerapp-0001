import http from "../http-common"; 

class MeetingNoteService {
  getAllMeetingNotes(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/meetingNote/meetingNotes`, searchDTO);
  }

  get(meetingNoteId) {
    return this.getRequest(`/meetingNote/${meetingNoteId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/meetingNote?field=${matchData}`, null);
  }

  addMeetingNote(data) {
    return http.post("/meetingNote/addMeetingNote", data);
  }

  update(data) {
  	return http.post("/meetingNote/updateMeetingNote", data);
  }
  
  uploadImage(data,meetingNoteId) {
  	return http.postForm("/meetingNote/uploadImage/"+meetingNoteId, data);
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

export default new MeetingNoteService();

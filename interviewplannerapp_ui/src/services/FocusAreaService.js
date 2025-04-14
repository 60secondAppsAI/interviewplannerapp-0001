import http from "../http-common"; 

class FocusAreaService {
  getAllFocusAreas(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/focusArea/focusAreas`, searchDTO);
  }

  get(focusAreaId) {
    return this.getRequest(`/focusArea/${focusAreaId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/focusArea?field=${matchData}`, null);
  }

  addFocusArea(data) {
    return http.post("/focusArea/addFocusArea", data);
  }

  update(data) {
  	return http.post("/focusArea/updateFocusArea", data);
  }
  
  uploadImage(data,focusAreaId) {
  	return http.postForm("/focusArea/uploadImage/"+focusAreaId, data);
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

export default new FocusAreaService();

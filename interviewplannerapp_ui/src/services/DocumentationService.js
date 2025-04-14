import http from "../http-common"; 

class DocumentationService {
  getAllDocumentations(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/documentation/documentations`, searchDTO);
  }

  get(documentationId) {
    return this.getRequest(`/documentation/${documentationId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/documentation?field=${matchData}`, null);
  }

  addDocumentation(data) {
    return http.post("/documentation/addDocumentation", data);
  }

  update(data) {
  	return http.post("/documentation/updateDocumentation", data);
  }
  
  uploadImage(data,documentationId) {
  	return http.postForm("/documentation/uploadImage/"+documentationId, data);
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

export default new DocumentationService();

import http from "../http-common"; 

class QuestionService {
  getAllQuestions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/question/questions`, searchDTO);
  }

  get(questionId) {
    return this.getRequest(`/question/${questionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/question?field=${matchData}`, null);
  }

  addQuestion(data) {
    return http.post("/question/addQuestion", data);
  }

  update(data) {
  	return http.post("/question/updateQuestion", data);
  }
  
  uploadImage(data,questionId) {
  	return http.postForm("/question/uploadImage/"+questionId, data);
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

export default new QuestionService();

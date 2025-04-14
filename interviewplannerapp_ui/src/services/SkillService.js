import http from "../http-common"; 

class SkillService {
  getAllSkills(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/skill/skills`, searchDTO);
  }

  get(skillId) {
    return this.getRequest(`/skill/${skillId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/skill?field=${matchData}`, null);
  }

  addSkill(data) {
    return http.post("/skill/addSkill", data);
  }

  update(data) {
  	return http.post("/skill/updateSkill", data);
  }
  
  uploadImage(data,skillId) {
  	return http.postForm("/skill/uploadImage/"+skillId, data);
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

export default new SkillService();

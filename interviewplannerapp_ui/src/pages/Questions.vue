<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <question-table
            v-if="questions && questions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:questions="questions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-questions="getAllQuestions"
             >

            </question-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import QuestionTable from "@/components/QuestionTable";
import QuestionService from "../services/QuestionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    QuestionTable,
  },
  data() {
    return {
      questions: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllQuestions(sortBy='questionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await QuestionService.getAllQuestions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.questions.length) {
					this.questions = response.data.questions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching questions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching question details:", error);
      }
    },
  },
  mounted() {
    this.getAllQuestions();
  },
  created() {
    this.$root.$on('searchQueryForQuestionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllQuestions();
    })
  }
};
</script>
<style></style>

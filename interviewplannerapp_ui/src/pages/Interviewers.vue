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
            <interviewer-table
            v-if="interviewers && interviewers.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:interviewers="interviewers"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-interviewers="getAllInterviewers"
             >

            </interviewer-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import InterviewerTable from "@/components/InterviewerTable";
import InterviewerService from "../services/InterviewerService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    InterviewerTable,
  },
  data() {
    return {
      interviewers: [],
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
    async getAllInterviewers(sortBy='interviewerId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await InterviewerService.getAllInterviewers(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.interviewers.length) {
					this.interviewers = response.data.interviewers;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching interviewers:", error);
        }
        
      } catch (error) {
        console.error("Error fetching interviewer details:", error);
      }
    },
  },
  mounted() {
    this.getAllInterviewers();
  },
  created() {
    this.$root.$on('searchQueryForInterviewersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllInterviewers();
    })
  }
};
</script>
<style></style>

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
            <interview-table
            v-if="interviews && interviews.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:interviews="interviews"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-interviews="getAllInterviews"
             >

            </interview-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import InterviewTable from "@/components/InterviewTable";
import InterviewService from "../services/InterviewService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    InterviewTable,
  },
  data() {
    return {
      interviews: [],
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
    async getAllInterviews(sortBy='interviewId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await InterviewService.getAllInterviews(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.interviews.length) {
					this.interviews = response.data.interviews;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching interviews:", error);
        }
        
      } catch (error) {
        console.error("Error fetching interview details:", error);
      }
    },
  },
  mounted() {
    this.getAllInterviews();
  },
  created() {
    this.$root.$on('searchQueryForInterviewsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllInterviews();
    })
  }
};
</script>
<style></style>

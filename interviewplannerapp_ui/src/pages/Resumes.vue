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
            <resume-table
            v-if="resumes && resumes.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:resumes="resumes"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-resumes="getAllResumes"
             >

            </resume-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ResumeTable from "@/components/ResumeTable";
import ResumeService from "../services/ResumeService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ResumeTable,
  },
  data() {
    return {
      resumes: [],
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
    async getAllResumes(sortBy='resumeId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ResumeService.getAllResumes(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.resumes.length) {
					this.resumes = response.data.resumes;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching resumes:", error);
        }
        
      } catch (error) {
        console.error("Error fetching resume details:", error);
      }
    },
  },
  mounted() {
    this.getAllResumes();
  },
  created() {
    this.$root.$on('searchQueryForResumesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllResumes();
    })
  }
};
</script>
<style></style>

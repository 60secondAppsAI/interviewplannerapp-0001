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
            <jobDescription-table
            v-if="jobDescriptions && jobDescriptions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:jobDescriptions="jobDescriptions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-job-descriptions="getAllJobDescriptions"
             >

            </jobDescription-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import JobDescriptionTable from "@/components/JobDescriptionTable";
import JobDescriptionService from "../services/JobDescriptionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    JobDescriptionTable,
  },
  data() {
    return {
      jobDescriptions: [],
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
    async getAllJobDescriptions(sortBy='jobDescriptionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await JobDescriptionService.getAllJobDescriptions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.jobDescriptions.length) {
					this.jobDescriptions = response.data.jobDescriptions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching jobDescriptions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching jobDescription details:", error);
      }
    },
  },
  mounted() {
    this.getAllJobDescriptions();
  },
  created() {
    this.$root.$on('searchQueryForJobDescriptionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllJobDescriptions();
    })
  }
};
</script>
<style></style>

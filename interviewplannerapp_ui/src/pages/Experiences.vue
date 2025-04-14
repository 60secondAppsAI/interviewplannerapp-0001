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
            <experience-table
            v-if="experiences && experiences.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:experiences="experiences"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-experiences="getAllExperiences"
             >

            </experience-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ExperienceTable from "@/components/ExperienceTable";
import ExperienceService from "../services/ExperienceService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ExperienceTable,
  },
  data() {
    return {
      experiences: [],
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
    async getAllExperiences(sortBy='experienceId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ExperienceService.getAllExperiences(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.experiences.length) {
					this.experiences = response.data.experiences;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching experiences:", error);
        }
        
      } catch (error) {
        console.error("Error fetching experience details:", error);
      }
    },
  },
  mounted() {
    this.getAllExperiences();
  },
  created() {
    this.$root.$on('searchQueryForExperiencesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllExperiences();
    })
  }
};
</script>
<style></style>

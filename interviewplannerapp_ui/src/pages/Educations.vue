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
            <education-table
            v-if="educations && educations.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:educations="educations"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-educations="getAllEducations"
             >

            </education-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import EducationTable from "@/components/EducationTable";
import EducationService from "../services/EducationService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    EducationTable,
  },
  data() {
    return {
      educations: [],
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
    async getAllEducations(sortBy='educationId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await EducationService.getAllEducations(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.educations.length) {
					this.educations = response.data.educations;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching educations:", error);
        }
        
      } catch (error) {
        console.error("Error fetching education details:", error);
      }
    },
  },
  mounted() {
    this.getAllEducations();
  },
  created() {
    this.$root.$on('searchQueryForEducationsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllEducations();
    })
  }
};
</script>
<style></style>

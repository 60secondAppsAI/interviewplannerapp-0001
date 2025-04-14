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
            <applicationForm-table
            v-if="applicationForms && applicationForms.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:applicationForms="applicationForms"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-application-forms="getAllApplicationForms"
             >

            </applicationForm-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ApplicationFormTable from "@/components/ApplicationFormTable";
import ApplicationFormService from "../services/ApplicationFormService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ApplicationFormTable,
  },
  data() {
    return {
      applicationForms: [],
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
    async getAllApplicationForms(sortBy='applicationFormId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ApplicationFormService.getAllApplicationForms(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.applicationForms.length) {
					this.applicationForms = response.data.applicationForms;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching applicationForms:", error);
        }
        
      } catch (error) {
        console.error("Error fetching applicationForm details:", error);
      }
    },
  },
  mounted() {
    this.getAllApplicationForms();
  },
  created() {
    this.$root.$on('searchQueryForApplicationFormsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllApplicationForms();
    })
  }
};
</script>
<style></style>

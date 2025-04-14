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
            <documentation-table
            v-if="documentations && documentations.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:documentations="documentations"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-documentations="getAllDocumentations"
             >

            </documentation-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import DocumentationTable from "@/components/DocumentationTable";
import DocumentationService from "../services/DocumentationService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    DocumentationTable,
  },
  data() {
    return {
      documentations: [],
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
    async getAllDocumentations(sortBy='documentationId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await DocumentationService.getAllDocumentations(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.documentations.length) {
					this.documentations = response.data.documentations;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching documentations:", error);
        }
        
      } catch (error) {
        console.error("Error fetching documentation details:", error);
      }
    },
  },
  mounted() {
    this.getAllDocumentations();
  },
  created() {
    this.$root.$on('searchQueryForDocumentationsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllDocumentations();
    })
  }
};
</script>
<style></style>

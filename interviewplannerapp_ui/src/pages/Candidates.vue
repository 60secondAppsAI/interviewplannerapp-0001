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
            <candidate-table
            v-if="candidates && candidates.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:candidates="candidates"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-candidates="getAllCandidates"
             >

            </candidate-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import CandidateTable from "@/components/CandidateTable";
import CandidateService from "../services/CandidateService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    CandidateTable,
  },
  data() {
    return {
      candidates: [],
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
    async getAllCandidates(sortBy='candidateId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await CandidateService.getAllCandidates(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.candidates.length) {
					this.candidates = response.data.candidates;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching candidates:", error);
        }
        
      } catch (error) {
        console.error("Error fetching candidate details:", error);
      }
    },
  },
  mounted() {
    this.getAllCandidates();
  },
  created() {
    this.$root.$on('searchQueryForCandidatesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllCandidates();
    })
  }
};
</script>
<style></style>

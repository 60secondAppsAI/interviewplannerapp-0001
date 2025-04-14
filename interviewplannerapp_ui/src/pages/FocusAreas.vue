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
            <focusArea-table
            v-if="focusAreas && focusAreas.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:focusAreas="focusAreas"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-focus-areas="getAllFocusAreas"
             >

            </focusArea-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import FocusAreaTable from "@/components/FocusAreaTable";
import FocusAreaService from "../services/FocusAreaService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    FocusAreaTable,
  },
  data() {
    return {
      focusAreas: [],
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
    async getAllFocusAreas(sortBy='focusAreaId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await FocusAreaService.getAllFocusAreas(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.focusAreas.length) {
					this.focusAreas = response.data.focusAreas;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching focusAreas:", error);
        }
        
      } catch (error) {
        console.error("Error fetching focusArea details:", error);
      }
    },
  },
  mounted() {
    this.getAllFocusAreas();
  },
  created() {
    this.$root.$on('searchQueryForFocusAreasChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllFocusAreas();
    })
  }
};
</script>
<style></style>

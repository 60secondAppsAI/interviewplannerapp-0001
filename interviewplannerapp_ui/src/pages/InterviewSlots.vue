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
            <interviewSlot-table
            v-if="interviewSlots && interviewSlots.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:interviewSlots="interviewSlots"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-interview-slots="getAllInterviewSlots"
             >

            </interviewSlot-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import InterviewSlotTable from "@/components/InterviewSlotTable";
import InterviewSlotService from "../services/InterviewSlotService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    InterviewSlotTable,
  },
  data() {
    return {
      interviewSlots: [],
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
    async getAllInterviewSlots(sortBy='interviewSlotId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await InterviewSlotService.getAllInterviewSlots(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.interviewSlots.length) {
					this.interviewSlots = response.data.interviewSlots;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching interviewSlots:", error);
        }
        
      } catch (error) {
        console.error("Error fetching interviewSlot details:", error);
      }
    },
  },
  mounted() {
    this.getAllInterviewSlots();
  },
  created() {
    this.$root.$on('searchQueryForInterviewSlotsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllInterviewSlots();
    })
  }
};
</script>
<style></style>

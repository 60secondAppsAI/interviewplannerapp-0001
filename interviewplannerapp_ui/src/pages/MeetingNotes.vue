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
            <meetingNote-table
            v-if="meetingNotes && meetingNotes.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:meetingNotes="meetingNotes"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-meeting-notes="getAllMeetingNotes"
             >

            </meetingNote-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import MeetingNoteTable from "@/components/MeetingNoteTable";
import MeetingNoteService from "../services/MeetingNoteService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    MeetingNoteTable,
  },
  data() {
    return {
      meetingNotes: [],
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
    async getAllMeetingNotes(sortBy='meetingNoteId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await MeetingNoteService.getAllMeetingNotes(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.meetingNotes.length) {
					this.meetingNotes = response.data.meetingNotes;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching meetingNotes:", error);
        }
        
      } catch (error) {
        console.error("Error fetching meetingNote details:", error);
      }
    },
  },
  mounted() {
    this.getAllMeetingNotes();
  },
  created() {
    this.$root.$on('searchQueryForMeetingNotesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllMeetingNotes();
    })
  }
};
</script>
<style></style>

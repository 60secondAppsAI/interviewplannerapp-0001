import Vue from "vue";
import VueRouter from "vue-router";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import Resumes from  '@/pages/Resumes.vue';
import ResumeDetail from  '@/pages/ResumeDetail.vue';
import JobDescriptions from  '@/pages/JobDescriptions.vue';
import JobDescriptionDetail from  '@/pages/JobDescriptionDetail.vue';
import FocusAreas from  '@/pages/FocusAreas.vue';
import FocusAreaDetail from  '@/pages/FocusAreaDetail.vue';
import Questions from  '@/pages/Questions.vue';
import QuestionDetail from  '@/pages/QuestionDetail.vue';
import Interviews from  '@/pages/Interviews.vue';
import InterviewDetail from  '@/pages/InterviewDetail.vue';
import Interviewers from  '@/pages/Interviewers.vue';
import InterviewerDetail from  '@/pages/InterviewerDetail.vue';
import InterviewSlots from  '@/pages/InterviewSlots.vue';
import InterviewSlotDetail from  '@/pages/InterviewSlotDetail.vue';
import Feedbacks from  '@/pages/Feedbacks.vue';
import FeedbackDetail from  '@/pages/FeedbackDetail.vue';
import Candidates from  '@/pages/Candidates.vue';
import CandidateDetail from  '@/pages/CandidateDetail.vue';
import Departments from  '@/pages/Departments.vue';
import DepartmentDetail from  '@/pages/DepartmentDetail.vue';
import Roles from  '@/pages/Roles.vue';
import RoleDetail from  '@/pages/RoleDetail.vue';
import Educations from  '@/pages/Educations.vue';
import EducationDetail from  '@/pages/EducationDetail.vue';
import Experiences from  '@/pages/Experiences.vue';
import ExperienceDetail from  '@/pages/ExperienceDetail.vue';
import Accounts from  '@/pages/Accounts.vue';
import AccountDetail from  '@/pages/AccountDetail.vue';
import ApplicationForms from  '@/pages/ApplicationForms.vue';
import ApplicationFormDetail from  '@/pages/ApplicationFormDetail.vue';
import Skills from  '@/pages/Skills.vue';
import SkillDetail from  '@/pages/SkillDetail.vue';
import Documentations from  '@/pages/Documentations.vue';
import DocumentationDetail from  '@/pages/DocumentationDetail.vue';
import Permissions from  '@/pages/Permissions.vue';
import PermissionDetail from  '@/pages/PermissionDetail.vue';
import MeetingNotes from  '@/pages/MeetingNotes.vue';
import MeetingNoteDetail from  '@/pages/MeetingNoteDetail.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../views/HomeView.vue"),
			redirect: '/resumes',
																			  },
  {
    path: "/pricing",
    name: "PricingView",
    component: () => import("../views/PricingView.vue"),
  },
  {
    path: "/arts-gallery",
    name: "ArtsGalleryView",
    component: () => import("../views/ArtsGalleryView.vue"),
  },
  {
    path: "/checkout/:id",
    name: "CheckoutView",
    component: () => import("../views/CheckoutView.vue"),
  },
  {
    path: "/stripe-checkout",
    name: "StripeCheckoutView",
    component: () => import("../views/StripeCheckoutView.vue"),
  },
	{
		path: '/resumes',
		name: 'Resumes',
		layout: DefaultLayout,
		component: Resumes,
	},
	{
	    path: '/resume/:resumeId', 
	    name: 'ResumeDetail',
		layout: DefaultLayout,
	    component: ResumeDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/jobDescriptions',
		name: 'JobDescriptions',
		layout: DefaultLayout,
		component: JobDescriptions,
	},
	{
	    path: '/jobDescription/:jobDescriptionId', 
	    name: 'JobDescriptionDetail',
		layout: DefaultLayout,
	    component: JobDescriptionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/focusAreas',
		name: 'FocusAreas',
		layout: DefaultLayout,
		component: FocusAreas,
	},
	{
	    path: '/focusArea/:focusAreaId', 
	    name: 'FocusAreaDetail',
		layout: DefaultLayout,
	    component: FocusAreaDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/questions',
		name: 'Questions',
		layout: DefaultLayout,
		component: Questions,
	},
	{
	    path: '/question/:questionId', 
	    name: 'QuestionDetail',
		layout: DefaultLayout,
	    component: QuestionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/interviews',
		name: 'Interviews',
		layout: DefaultLayout,
		component: Interviews,
	},
	{
	    path: '/interview/:interviewId', 
	    name: 'InterviewDetail',
		layout: DefaultLayout,
	    component: InterviewDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/interviewers',
		name: 'Interviewers',
		layout: DefaultLayout,
		component: Interviewers,
	},
	{
	    path: '/interviewer/:interviewerId', 
	    name: 'InterviewerDetail',
		layout: DefaultLayout,
	    component: InterviewerDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/interviewSlots',
		name: 'InterviewSlots',
		layout: DefaultLayout,
		component: InterviewSlots,
	},
	{
	    path: '/interviewSlot/:interviewSlotId', 
	    name: 'InterviewSlotDetail',
		layout: DefaultLayout,
	    component: InterviewSlotDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/feedbacks',
		name: 'Feedbacks',
		layout: DefaultLayout,
		component: Feedbacks,
	},
	{
	    path: '/feedback/:feedbackId', 
	    name: 'FeedbackDetail',
		layout: DefaultLayout,
	    component: FeedbackDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/candidates',
		name: 'Candidates',
		layout: DefaultLayout,
		component: Candidates,
	},
	{
	    path: '/candidate/:candidateId', 
	    name: 'CandidateDetail',
		layout: DefaultLayout,
	    component: CandidateDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/departments',
		name: 'Departments',
		layout: DefaultLayout,
		component: Departments,
	},
	{
	    path: '/department/:departmentId', 
	    name: 'DepartmentDetail',
		layout: DefaultLayout,
	    component: DepartmentDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/roles',
		name: 'Roles',
		layout: DefaultLayout,
		component: Roles,
	},
	{
	    path: '/role/:roleId', 
	    name: 'RoleDetail',
		layout: DefaultLayout,
	    component: RoleDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/educations',
		name: 'Educations',
		layout: DefaultLayout,
		component: Educations,
	},
	{
	    path: '/education/:educationId', 
	    name: 'EducationDetail',
		layout: DefaultLayout,
	    component: EducationDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/experiences',
		name: 'Experiences',
		layout: DefaultLayout,
		component: Experiences,
	},
	{
	    path: '/experience/:experienceId', 
	    name: 'ExperienceDetail',
		layout: DefaultLayout,
	    component: ExperienceDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/accounts',
		name: 'Accounts',
		layout: DefaultLayout,
		component: Accounts,
	},
	{
	    path: '/account/:accountId', 
	    name: 'AccountDetail',
		layout: DefaultLayout,
	    component: AccountDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/applicationForms',
		name: 'ApplicationForms',
		layout: DefaultLayout,
		component: ApplicationForms,
	},
	{
	    path: '/applicationForm/:applicationFormId', 
	    name: 'ApplicationFormDetail',
		layout: DefaultLayout,
	    component: ApplicationFormDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/skills',
		name: 'Skills',
		layout: DefaultLayout,
		component: Skills,
	},
	{
	    path: '/skill/:skillId', 
	    name: 'SkillDetail',
		layout: DefaultLayout,
	    component: SkillDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/documentations',
		name: 'Documentations',
		layout: DefaultLayout,
		component: Documentations,
	},
	{
	    path: '/documentation/:documentationId', 
	    name: 'DocumentationDetail',
		layout: DefaultLayout,
	    component: DocumentationDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/permissions',
		name: 'Permissions',
		layout: DefaultLayout,
		component: Permissions,
	},
	{
	    path: '/permission/:permissionId', 
	    name: 'PermissionDetail',
		layout: DefaultLayout,
	    component: PermissionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/meetingNotes',
		name: 'MeetingNotes',
		layout: DefaultLayout,
		component: MeetingNotes,
	},
	{
	    path: '/meetingNote/:meetingNoteId', 
	    name: 'MeetingNoteDetail',
		layout: DefaultLayout,
	    component: MeetingNoteDetail,
	    props: true // Pass route params as props to the component
  	},
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes,
});

export default router;

import { createStore, action, thunk } from 'easy-peasy';

import { api } from './utils/apiClient';
import {ratingArea, score} from "./utils/const";

// Documentation for easy-peasy: https://easy-peasy.vercel.app/docs/tutorials/quick-start.html

// first define the store model, then add actions to change the stored state and add 'thunks' for actions with side effects (e.g. api calls)

const projectListModel = {
  items: [], // list of: {"projectID": 2,"projectName": "Mock Project" }

  set: action((state, items) => {
    state.items = items;
  }),
  add: action((state, newProject) => {
    state.items.push(newProject);
  }),

  fetch: thunk(async (actions, payload) => {
    await api
      .url('/projects')
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
  }),
};

const projectModel = {
  data: {
    projectID: 0,
    projectName: '',
    members: [],
    productAreas: [],
  },
  // general actions
  set: action((state, project) => {
    state.data = project;
  }),
  update: action((state, updatedProps) => {
    state.data = { ...state.data, ...updatedProps };
  }),

  setProjectName: action((state, projectName) => {
    state.data.projectName = projectName;
  }),
  addMember: action((state, newMember) => {
    state.data.members.push(newMember);
  }),
  removeMember: action((state, member) => {
    // remove member with matching email from items
    state.data.members = state.data.members.filter((m) => m.email !== member.email);
  }),
  updateMember: action((state, member) => {
    // overwrite member with same email
    const index = state.data.members.map((m) => m.email).indexOf(member.email); // get index of member with same email. if not found, index=-1
    state.data.members[index] = { ...state.data.members[index], ...member };
  }),
  addProductArea: action((state, newArea) => {
    state.data.productAreas.push(newArea);
  }),
  removeProductArea: action((state, areaID) => {
    // remove member with matching email from items
    state.data.productAreas = state.data.productAreas.filter((aID) => aID !== areaID);
  }),

  // GET project by id
  fetch: thunk(async (actions, id) => {
    await api
      .url('/projects/' + id)
      .get()
      .json((json) => actions.updateProject(json))
      .catch(console.error);
  }),

  // POST new Project
  sendCreate: thunk(async (actions, newProject) => {
    console.log(newProject);
    await api
      .url('/projects')
      .post(newProject)
      .json((json) => actions.set(json))
      .catch(console.error);
  }),
};

const ratingModel =
    {
      data: {
        ratings:[]
      },

      init: action((state, payload) => {
        state.data = [
          {
            productID: 0,
            ratingID: 0,
            answer: 'test answer',
            comment: 'test comment',
            score: score.gering,
            rating: {
              ratingID: 0,
              category: 'Treiber 1',
              criterion: 'test frage',
              ratingArea: ratingArea.ECONOMIC
            },
          },
        ]
      }),

      // general actions
      set: action((state, ratings) => {
        state.data = ratings;
      }),
      update: action((state, updatedProps) => {
        state.data = { ...state.data, ...updatedProps };
      }),
      updateRating: action((state, rating) => {
        const index = state.data.ratings.map((r) => r.ratingID).indexOf(rating.ratingID); // get index of member with same email. if not found, index=-1
        state.data.ratings[index] = { ...state.data.ratings[index], ...rating };
      }),

      // GET all ratings
      fetch: thunk(async (actions,) => {
        await api
            .url('/ratings' )
            .get()
            .json((json) => actions.set(json))
            .catch(console.error);
      }),

    };

const store = createStore({
  projectList: projectListModel,
  project: projectModel,
  rating: ratingModel,
});

export default store;

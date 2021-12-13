import { createStore, action, thunk } from 'easy-peasy';

import { api } from './utils/apiClient';

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
  // to change name pass {name: 'new name' } as payload
  update: action((state, updatedProps) => {
    state.project = { ...state.project, ...updatedProps };
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
  create: thunk(async (actions, newProject) => {
    console.log(newProject);
    await api.url('/projects').post(newProject).res(console.log);
  }),
};

const store = createStore({
  projectList: projectListModel,
  project: projectModel,
});

export default store;

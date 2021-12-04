import { createStore, action, thunk } from 'easy-peasy';

import { api } from './utils/apiClient';

// Documentation for easy-peasy: https://easy-peasy.vercel.app/docs/tutorials/quick-start.html

// first define the store model, then add actions to change the stored state and add 'thunks' for actions with side effects (e.g. api calls)
export const store = createStore({
  project: {
    projectID: 0,
    projectName: '',
    members: [],
    productAreas: [],
  },

  updateProject: action((state, payload) => {
    state.project = { ...state.project, ...payload }; // to change name pass {name: 'new name' } as payload
  }),

  fetchProject: thunk(async (actions, payload) => {
    await api
      .url('/projects/' + payload.projectID)
      .get()
      .json((json) => actions.updateProject(json))
      .catch(console.error);
  }),
});

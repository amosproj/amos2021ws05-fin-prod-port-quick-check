import { createStore, action, thunk } from 'easy-peasy';

import { api } from './utils/apiClient';

// Documentation for easy-peasy: https://easy-peasy.vercel.app/docs/tutorials/quick-start.html

// first define the store model, then add actions to change the stored state and add 'thunks' for actions with side effects (e.g. api calls)

const productAreaModel = {
  products: [
    /*{
      productID: 0,
      productName: '',
      productArea: {
        "id": "1",
        "name": "Kredit",
      "category": "Privat"},
      projectID: 1,
      parentID: 0,
    }*/
  ],
  set: action((state, products) => {
    state.products = products;
  }),
  addProduct: action((state, product) => {
    state.products.push(product);
    console.log('react schmitz');
  }),
  removeProduct: action((state, product) => {
    state.products = state.products.filter((p) => p.productName !== product.productName);
  }),
};

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

const store = createStore({
  projectList: projectListModel,
  project: projectModel,
  productList: productAreaModel,
});

export default store;

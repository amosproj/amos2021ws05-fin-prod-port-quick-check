import { createStore, action, thunk, computed } from 'easy-peasy';

import { api } from './utils/apiClient';
import { ratingArea, score } from './utils/const';

// Documentation for easy-peasy: https://easy-peasy.vercel.app/docs/tutorials/quick-start.html

// first define the store model, then add actions to change the stored state and add 'thunks' for actions with side effects (e.g. api calls)

const productAreaModel = {
  items: [
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

  products: computed((state) => state.items.filter((p) => p.parentID === 0)),

  getVariants: computed((state) => {
    return (product) => state.items.filter((p) => p.parentID === product.productID);
  }),

  getAreaProducts: computed((state) => {
    return (areaID) => state.products.filter((p) => p.productArea.id === areaID);
  }),

  set: action((state, products) => {
    state.items = products;
  }),
  addProduct: action((state, product) => {
    state.items.push(product);
  }),
  changeProductName: action((state, product) => {
    const index = state.products.map((p) => p.productID).indexOf(product.productID);
    // get index of member with same email. if not found, index=-1
    state.products[index] = { ...state.products[index], productName: product.productName };
  }),
  changeProductComment: action((state, product) => {
    const index = state.products.map((p) => p.productID).indexOf(product.productID);
    // get index of member with same email. if not found, index=-1
    state.products[index] = { ...state.products[index], comment: product.comment };
  }),
  removeProduct: action((state, product) => {
    state.items = state.products.filter((p) => p.productID !== product.productID);
  }),
  fetch: thunk(async (actions, id) => {
    console.log('/projects/' + id + '/products');
    await api
      .url('/projects/' + id + '/products')
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
  }),
  createProduct: thunk(async (actions, newProduct) => {
    //console.log(JSON.stringify({projectID, ...newProduct}))
    console.log(JSON.stringify(newProduct));
    await api
      .url('/projects/' + newProduct.projectID + '/products')
      .post(newProduct)
      .res()
      .catch(console.error);
    actions.fetch(newProduct.projectID);
  }),
  updateAllProducts: thunk(async (actions, products) => {
    products.map((product) => actions.updateProduct(product));
  }),

  updateProduct: thunk(async (actions, product) => {
    await api
      .url('/products/' + product.productID)
      .put(product)
      .res()
      .catch(console.error);
  }),
};

const resultModel = {
  results: [],
  set: action((state, results) => {
    state.results = results;
  }),
  fetch: thunk(async (actions, id) => {
    console.log('/projects/' + id + '/results');
    await api
      .url('/projects/' + id + '/results')
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
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
    creatorID: '0fef539d-69be-4013-9380-6a12c3534c67',
    members: [],
    productAreas: [],
  },

  init: action((state, payload) => {
    state.data = {
      projectID: 0,
      creatorID: '0fef539d-69be-4013-9380-6a12c3534c67',
      projectName: '',
      members: [],
      productAreas: [],
    };
  }),
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
    state.data.members = state.data.members.filter((m) => m.userEmail !== member.userEmail);
  }),
  updateMember: action((state, member) => {
    // overwrite member with same email
    const index = state.data.members.map((m) => m.userEmail).indexOf(member.userEmail); // get index of member with same email. if not found, index=-1
    state.data.members[index] = { ...state.data.members[index], ...member };
  }),
  addProductArea: action((state, newArea) => {
    state.data.productAreas.push(newArea);
  }),
  removeProductArea: action((state, remArea) => {
    // remove member with matching email from items
    state.data.productAreas = state.data.productAreas.filter((a) => a.id !== remArea.id);
  }),

  // GET project by id
  fetch: thunk(async (actions, id) => {
    await api
      .url('/projects/' + id)
      .get()
      .json((json) => actions.set(json))
      // .json((json) => console.log(json))
      .catch(console.error);
  }),

  // POST new Project
  sendCreate: thunk(async (actions, projectData) => {
    console.log('send CREATE project:', { projectData });
    await api
      .url('/projects')
      .post(projectData)
      .json((json) => actions.set(json))
      .catch(console.error);
  }),

  sendUpdate: thunk(async (actions, projectData) => {
    console.log('send UPDATE project:', { projectData });
    actions.set(projectData);
    await api
      .url(`/projects/` + String(projectData.projectID))
      .put(projectData)
      .res(console.log)
      .catch(console.error);

    actions.set(projectData);
  }),
};

const ratingModel = {
  ratings: [],

  init: action((state, payload) => {
    state.ratings = [
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
          ratingArea: ratingArea.ECONOMIC,
        },
      },
    ];
  }),

  // general actions
  set: action((state, ratings) => {
    state.ratings = ratings;
  }),
  update: action((state, updatedProps) => {
    state.ratings = { ...state.ratings, ...updatedProps };
  }),

  // GET all ratings
  fetch: thunk(async (actions, productID) => {
    await api
      .url('/products/' + productID + '/ratings')
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);
  }),

  createNew: thunk(async (actions, product) => {
    //console.log('send UPDATE project:', { projectData });
    actions.set(product);
    await api
      .url(`/products/` + String(product.productID) + '/ratings')
      .post(product)
      .res(console.log)
      .catch(console.error);

    actions.set(product);
  }),

  sendUpdate: thunk(async (actions, product) => {
    //console.log('send UPDATE project:', { projectData });
    actions.set(product);
    await api
      .url(`/products/` + String(product.productID) + '/ratings')
      .put(product)
      .res(console.log)
      .catch(console.error);

    actions.set(product);
  }),
};

const store = createStore({
  projectList: projectListModel,
  project: projectModel,
  rating: ratingModel,
  productList: productAreaModel,
  resultList: resultModel,
});

export default store;

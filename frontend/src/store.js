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

  updateProductName: action((state, { productID, newName }) => {
    const index = state.items.map((p) => p.productID).indexOf(productID);
    state.items[index] = { ...state.items[index], productName: newName };
  }),
  updateProductComment: action((state, { productID, newComment }) => {
    const index = state.items.map((p) => p.productID).indexOf(productID);
    state.items[index] = { ...state.items[index], comment: newComment };
  }),
  removeProduct: action((state, product) => {
    state.items = state.items.filter((p) => p.productID !== product.productID);
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

/** state model for individual projects */
const projectModel = {
  data: {
    projectID: 0,
    projectName: '',
    creatorID: '',
    members: [],
    productAreas: [],
  },
  /** set the project data to its initial state. */
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
  /** Overwrite the the complete stored project with a new project
   * @param {Object} project - Project that will be stored
   * @example
   *    set({projectID: 1, projectName: 'Foo', creatorID: 'max', members: [{...}], ...})
   */
  set: action((state, project) => {
    state.data = project;
  }),

  /** Set the project attributes to a new value if provided in the parameter `updatedProps`
   * @param {Object} updatedProps - project attributes and their new values
   * @example
   *    update({projectName: 'NewName' })
   *    --> will set the projectName attribute of the stored project to value `NewName`
   */
  update: action((state, updatedProps) => {
    state.data = { ...state.data, ...updatedProps };
  }),

  /** Set the project name to a new value
   * @param {string} projectName
   */
  setProjectName: action((state, projectName) => {
    state.data.projectName = projectName;
  }),

  /** Add a member to the stored project
   * @param {Object} Member - member that will be added to the project
   * @example
   *    addMember({userID: "1234", userName: "Max", userEmail: "max@amos.de", role:  "CLIENT"})
   */
  addMember: action((state, newMember) => {
    state.data.members.push(newMember);
  }),
  /**
   * removes member from a project
   * @param {Object} member - member which should be removed from the project, only the userEmail has to be provided in the member object
   * @example
   *    removeMember({userEmail: 'max@amos.de'})
   */
  removeMember: action((state, member) => {
    // remove member with matching email from items
    state.data.members = state.data.members.filter((m) => m.userEmail !== member.userEmail);
  }),

  /**
   * update the stored attributes for a single project member
   * @param {Object} member - object that contains the member email as well as the updated attributes
   * @example
   *    removeMember({userEmail: 'max@amos.de', name: 'Maximilian'})
   */
  updateMember: action((state, member) => {
    // overwrite member with same email
    const index = state.data.members.map((m) => m.userEmail).indexOf(member.userEmail); // get index of member with same email. if not found, index=-1
    state.data.members[index] = { ...state.data.members[index], ...member };
  }),
  /** add product area to the project */
  addProductArea: action((state, newArea) => {
    state.data.productAreas.push(newArea);
  }),

  /** remove a product area from the project */
  removeProductArea: action((state, remArea) => {
    // remove member with matching email from items
    state.data.productAreas = state.data.productAreas.filter((a) => a.id !== remArea.id);
  }),

  /**
   * fetch and store a project by projectID from the backend
   * @param {string} projectID - ID of the project that will be fetched from the backend
   * @example
   *   fetch(1) --> fetch and store project with id 1
   */
  fetch: thunk(async (actions, id) => {
    await api
      .url('/projects/' + id)
      .get()
      .json((json) => actions.set(json))
      // .json((json) => console.log(json))
      .catch(console.error);
  }),

  /**
   * create a new project in the backend
   * @param {Object} projectData - project Object that will be created in the backend
   */
  sendCreate: thunk(async (actions, projectData) => {
    console.log('send CREATE project:', { projectData });
    await api
      .url('/projects')
      .post(projectData)
      .json((json) => actions.set(json))
      .catch(console.error);
  }),

  /**
   * update attributes of a project stored in the backend
   * @param {Object} projectData - project Object that will be updated in the backend, `projectID`  must be set as a project attribute
   */
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

const productRatingModel = {
  product: {
    productID: -1,
    ratingID: 0,
    ratings: [
      {
        ratingID: -1,
        answer: '',
        comment: '',
        score: score.GERING,
        rating: {
          category: '',
          criterion: '',
          ratingArea: ratingArea.ECONOMIC,
        },
      },
    ],
  },
  categories: [],

  makeCategories: action((state, payload) => {
    // run in fetch action
    const ratingCategories = state.product.ratings.map((r) => r.rating.category);
    state.categories = [...new Set(ratingCategories)];
  }),

  getRatingsByCategory: computed((state) => {
    return (category) =>
      state.product.ratings.filter((rating) => rating.rating.category === category);
  }),

  init: action((state, payload) => {
    state.product = [
      {
        productID: -1,
        ratingID: 0,
        answer: 'test answer',
        comment: 'test comment',
        score: score.GERING,
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
  set: action((state, product) => {
    state.product = product;
  }),

  update: action((state, updatedProps) => {
    state.product = { ...state.product, ...updatedProps };
  }),

  updateRating: action((state, rating) => {
    // overwrite single rating with same id
    let index = state.product.ratings.map((r) => r.ratingID).indexOf(rating.ratingID);
    state.product.ratings[index] = { ...state.product.ratings[index], ...rating };
  }),

  // GET all ratings
  fetch: thunk(async (actions, tmp) => {
    await api
      .url('/products/' + tmp[0] + '/ratings?ratingArea=' + tmp[1].toUpperCase())
      .get()
      .json((json) => actions.set(json))
      .catch(console.error);

    actions.makeCategories();
  }),

  sendUpdate: thunk(async (actions, product) => {
    await api
      .url('/products/' + product.productID + '/ratings')
      .put(product)
      .res(console.log)
      .catch(console.error);
    actions.set(product);
  }),
};

const store = createStore({
  projectList: projectListModel,
  project: projectModel,
  rating: productRatingModel,
  productList: productAreaModel,
  resultList: resultModel,
});

export default store;

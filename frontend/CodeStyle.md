# FrontEnd CodeStyle
Hi everbody please follow these ideas, will help to join code :D

## Naming

**Files**, and **Components** are to be named useing **PascalCase** (first letter of every word capital, no _ - ect.).

**Instances** of **Components** are in **camelCase** (first word , first letter small, all following words capitlize first letter).

**Folder names** are also **camelCase.**

## Folder Structure

* **components**
		- All components that are being used in at least two different pages.
		
* **pages**
	- Single page files that share all their imports.
	
	- **Page folders**. Name = \<name of page\>
		- Page.jsx: the main page
		
		- **components**
			- Components only this page can use. (Ex. MemberRow, and MemberTable for the page ManageProject)


##Import Style

```
//Foreign
import React from 'react';
import {
  Example
} from '@chakra-ui/react';
import { ExampleIcon } from '@chakra-ui/icons';

//Utils
import { api } from '../utils/apiClient';

//Components - shared components
import * from '../components/*';

//Page - page's components
import * from './components'


```

****
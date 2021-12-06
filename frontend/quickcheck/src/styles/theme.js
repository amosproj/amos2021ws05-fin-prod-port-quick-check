import { extendTheme } from '@chakra-ui/react';

import { ButtonStyles as Button } from './Button';
import { FlexStyle as Flex } from './FlexStyle'
import { darken, whiten, mode} from '@chakra-ui/theme-tools'

const config = {
  initialColorMode: 'light',
  useSystemColorMode: false,
};

const colors = {
  brand: {
    100: 'white',
    200: 'yellow',
    300: 'orange',
    400: 'red',
    500: 'green',
    600: 'blue',
    700: 'pruple',
    800: 'brown',
    900: 'black',
  },
  bp_button: {
    200: '#29D5FF',
    300: '#6C03A8',
    500: '#29D5FF',
    600: '#6C03A8',
  },
// BearingPoint style guide: https://www.bearingpoint.com/styleguide/en/general/#Typography
  bp: {
    green: '#93D522',
    lblue: '#29D5FF',
    blue: '#033E8E',
    pink: '#FF48A6',
    lpurple: '#6C03A8',
    purple: '#520881',

    text: '#222222',
    warning: '#FFF300',
    alert: '#D52B1E',
  // background    
    white: '#ffffff',
    lightgray: '#f5f5f5',
    gray: '#e8e8e8',
    dark: '#313131',

    // default colors
    primary: '#29D5FF',
    secondary: '#6C03A8', 
  },
  bg: {
    100: '#ffffff',
    200: '#f5f5f5',
    300: '#e8e8e8',
    400: '#313131',
  },

  bg: {
    lighter: '#ffffff',
    light: '#f5f5f5',
    dark: '#313131',
    darker: '#111111',
  },
  text: '#222222',
};

// check out  https://github.com/chakra-ui/chakra-ui/issues/591
// for info on how to change default light/dark colors of components


const components = {
  Button, Flex, 
  Heading: {
    baseStyle: {
      color:'bp.lblue',
    },
  },
  Text: {
    color: 'text'
  }
};


const layerStyles = {
  card_light: {
    bg: whiten('gray.200', 80),
    border: '1px',
    borderColor: 'black'
  }, 
  card_dark: {
    bg: 'gray.200',
    border: '1px'
  }
}

const fonts = {
  heading: '"BearingPointSans","FS Albert",Calibri,Helvetica,"Trebuchet MS",Arial,sans-serif',
};

const theme = extendTheme({ config, colors, components, fonts, layerStyles });

export default theme;

// bearingPoint gradients: All gradients use an angle of 135deg. Their color start is set to 10% and their color end is set to 90%.
// - (primary, secondary)
// - (pink, purple)
// - (green, blue)

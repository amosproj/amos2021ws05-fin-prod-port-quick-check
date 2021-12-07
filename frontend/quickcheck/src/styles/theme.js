import { extendTheme } from '@chakra-ui/react';

import { ButtonStyles as Button } from './Button';
import { TextStyles as Text } from './Text';
import layerStyles from './layerStyles';
// import { darken, whiten, mode } from '@chakra-ui/theme-tools';

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
    lightgray: '#f5f5f5',
    gray: '#e8e8e8',
    dark: '#313131',

    // default colors
    primary: '#29D5FF',
    secondary: '#6C03A8',
  },
  text: '#222222',
  primary: '#29D5FF',
  secondary: '#6C03A8',

};

// check out  https://github.com/chakra-ui/chakra-ui/issues/591
// for info on how to change default light/dark colors of components

const components = {
  Button, Text,
  Heading: {
    baseStyle: {
    },
  },
};


const fonts = {
  heading: '"BearingPointSans","FS Albert",Calibri,Helvetica,"Trebuchet MS",Arial,sans-serif',
};

const theme = extendTheme({ config, colors, components, fonts, layerStyles });

export default theme;

// bearingPoint gradients: All gradients use an angle of 135deg. Their color start is set to 10% and their color end is set to 90%.
// - (primary, secondary)
// - (pink, purple)
// - (green, blue)

import { extendTheme } from '@chakra-ui/react';

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
  bearingpoint: {
    green: '#93D522',
    lightblue: '#29D5FF',
    blue: '#033E8E',
    pink: '#FF48A6',
    lightpurple: '#6C03A8',
    purple: '#520881',

    warning: '#FFF300',
    alert: '#D52B1E',

    // used for clickable elements
    primary: '#29D5FF', // light blue
    // used for hover or focus
    secondary: '#6C03A8', // light purple
  },
  bg: {
    100: '#ffffff',
    200: '#f5f5f5',
    300: '#e8e8e8',
    400: '#313131',
  },
};

// check out  https://github.com/chakra-ui/chakra-ui/issues/591
// for info on how to change default light/dark colors of components

const components = {
  Text: {
    variants: {
      brand: {
        rounded: 'md',
        color: 'gray.200',
        bg: 'teal.400',
        p: 10,
        _hover: {
          color: 'blue.800',
          boxShadow: '2xl',
        },
      },
    },
  },
};

const fonts = {
  heading: '"BearingPointSans","FS Albert",Calibri,Helvetica,"Trebuchet MS",Arial,sans-serif',
};

const theme = extendTheme({ config, colors, components, fonts });

export default theme;

// bearingPoint gradients: All gradients use an angle of 135deg. Their color start is set to 10% and their color end is set to 90%.
// - (primary, secondary)
// - (pink, purple)
// - (green, blue)

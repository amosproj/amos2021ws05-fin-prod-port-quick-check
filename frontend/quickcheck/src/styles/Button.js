import { darken, whiten, mode} from '@chakra-ui/theme-tools'



export const ButtonStyles = {
    // Styles for the base style
    baseStyle: {},
    // Styles for the size variations
    sizes: {},
    // Styles for the visual style variations
    variants: {
      card: (props) => ({
        bg: mode('brand.300', 'brand.600')(props),
        _hover: {
          bg: mode(darken('brand.300', 20), whiten('brand.600', 20))(props),
          transform: 'scale(1.25)'
        }
      }),
      secondary: (props) => ({
        bg: 'transparent',
        border: '1px solid',
        borderColor: 'blue',
        color:  'blue',
        _hover: {
          bg: mode('blue', whiten('brand.600', 20))(props),
          color: 'white',
          border: '0px',
          transform: 'scale(1.1)'
        }
      })
    },
    // The default `size` or `variant` values
    defaultProps: {},
  }

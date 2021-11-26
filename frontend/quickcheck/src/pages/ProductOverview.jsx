import React from 'react'
import { SimpleGrid, Box, CircularProgress, CircularProgressLabel, IconButton  } from "@chakra-ui/react"
import Page from '../components/Page'
import { Tabs, TabList, TabPanels, Tab, TabPanel, Wrap, WrapItem, Center } from "@chakra-ui/react"
import { Textarea, Text } from "@chakra-ui/react"
import { Button } from '@chakra-ui/button'

export default function ProductOverview() {
  let [value, setValue] = React.useState("")

  let handleInputChange = (e) => {
    let inputValue = e.target.value
    setValue(inputValue)
  }
  return (
    <div>
      <Page title="Product Overview">
        <Tabs>
          <TabList>
            <Tab>Kredit</Tab>
            <Tab>Payments</Tab>
            <Tab>Customer</Tab>
          </TabList>

          <TabPanels>
            <TabPanel>
            <Wrap>
              <WrapItem>
                <Center>
                
              
                  <SimpleGrid columns={5} spacing={10}>
                    <Box bg="teal.300" color="black" borderRadius="md" textAlign="Center">Produkte</Box>
                    <Box bg="teal.300" color="black" borderRadius="md" textAlign="Center">Wirtschaftliche Bewertung</Box>
                    <Box bg="teal.300" color="black" borderRadius="md" textAlign="Center">Komplexitäts- bewertung</Box>
                    <Box bg="teal.300" color="black" borderRadius="md" textAlign="Center">Anmerkung</Box>
                    <Box bg="teal.300" color="black" borderRadius="md" textAlign="Center">Nachweis </Box>
                  </SimpleGrid>
              </Center>
            </WrapItem>
            <WrapItem>
              <Center>
              <SimpleGrid columns={5} spacing={10}>

                <Box bg="gray.300" color="black" borderRadius="md" textAlign="Center">Dispokredit</Box>
                <Box borderRadius="md" textAlign="Center">
                <IconButton colorScheme="blue" aria-label="Search database"  />
                  <CircularProgress value={40} color="green.400">
                    <CircularProgressLabel>40%</CircularProgressLabel>
                  </CircularProgress></Box>
                <Box bg="gray.300" color="black" borderRadius="md" textAlign="Center">Komplexitäts- bewertung</Box>
                <Box bg="gray.300" color="black" borderRadius="md" textAlign="Center">Anmerkung</Box>
                <Box borderRadius="md" textAlign="Center">
                  <><Text mb="8px">Value: {value}</Text>
                    <Textarea value={value} onChange={handleInputChange} placeholder="Here is a sample placeholder" size="sm"/>
                  </>
                </Box>
                
              </SimpleGrid>
              </Center>
            </WrapItem>
              </Wrap>
              <Button size="lg">
        Add new
      </Button>
            </TabPanel>
            <TabPanel>
              <p>two!</p>
            </TabPanel>
            <TabPanel>
              <p>three!</p>
            </TabPanel>
          </TabPanels>
        </Tabs>
      </Page>
    </div>
  )
}

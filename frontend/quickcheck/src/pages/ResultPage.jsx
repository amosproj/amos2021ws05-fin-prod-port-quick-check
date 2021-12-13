import React from 'react'
import Card from '../components/Card';
import Page from '../components/Page';
import { List, Button, Heading, VStack, Text, Flex, Square, Center, Box, HStack, Input, TextArea, Spacer } from '@chakra-ui/react';
import { useState } from 'react';
// import { Link } from 'react-router-dom';

const mock = [
    {
        name: "Interview 1",
        author: "T.S.",
        id: 0
    },
    {
        name: "Interview 1",
        author: "T.S.",
        id: 1
    }
]

function sourceRow({ source, key }) {
    return (
      <Card layerStyle="card_bordered">
        <Heading color="primary" size="lg" align="center" w="40%" maxW="50%">
          {source.name}
        </Heading>
        <Spacer />
        <VStack p={2}>
          <Text fontWeight="bolder" fontSize="md">
            {source.author}
          </Text>
          <Text fontSize="sm" color="gray.400">
            Author
          </Text>
        </VStack>
      </Card>
    );
  }

export default function ResultPage() {
    const [sources, setSources] = useState(mock)
    return (
        <div>
            <Page title="Results">
                <Card>
                    <Flex flexDirection="row" columnGap={100} w="full" justifyContent="space-between" alignItems="stretch">
                    {sources.map((source) => (
                            <sourceRow source={source} key={source.ID} />
                        ))}
                    </Flex>

                </Card>
                <HStack>
                    <Button> Export Results </Button>
                    <Button> Back</Button>
                </HStack>
                <p>{JSON.stringify(sources)}</p>
            </Page>
        </div>
    )
}

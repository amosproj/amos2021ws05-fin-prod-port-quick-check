import {
    Flex,
    Box,
    Input,
    Heading,
    Editable,
    EditableInput,
    EditablePreview,
    Button,
    CircularProgress,
    CircularProgressLabel,
    Spacer,
    Textarea,
} from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';

export default function RatingRow({editable})
{
    return (
        <div>
            <Card
                layerStyle="card_bordered"
                justifyContent="space-between"
                // w={(parentID > 0) ? ' 90%' : 'full'}
                _hover={{ boxShadow: '2xl' }}
                align="center"
            >
                <Input
                    align="center"
                    size="md"
                    w="100%"
                    isDisabled={!editable}
                    onChange={(e) => {
                        console.log(e.target.value);
                    }}
                    value={"Frage"}
                />
            </Card>
            <Card
                layerStyle="card_bordered"
                justifyContent="space-between"
                // w={(parentID > 0) ? ' 90%' : 'full'}
                _hover={{ boxShadow: '2xl' }}
            >
                <Spacer />
                <Textarea width="30%" placeholder="Antwort" />
                <Spacer />
                <Input
                    align="center"
                    size="md"
                    w="25%"
                    isDisabled={!editable}
                    onChange={(e) => {
                        console.log(e.target.value);
                    }}
                    value={"Beurteilung"}
                />
                <Spacer />
                <Textarea width="30%" placeholder="Anmerkung" />
                <Spacer />
                <Input
                    align="center"
                    size="md"
                    w="25%"
                    isDisabled={!editable}
                    onChange={(e) => {
                        console.log(e.target.value);
                    }}
                    value={"Upload"}
                />
            </Card>
        </div>
    );
}